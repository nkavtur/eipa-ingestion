# Sample EIPA Ingestion App

A very simple spring boot app for grabbing and producing unique EIPA dynamic data.  
___

## Local Testing

### Prerequisites

* Java 11
* Docker-compose
* Maven

### Building the application

Run the following to build the app:

    $ mvn clean install

Run the following to launch Redis:

    $ docker-compose up

### Running the application

After successful build, you should find eipa-ingestion-app-0.0.1-SNAPSHOT.jar in the target directory.

Run the following to launch it:
```
java -jar \
    target/eipa-ingestion-app-0.0.1-SNAPSHOT.jar \
    --application.integration.sink.url='https://httpbin.org/post1'
```

You should see a bunch of generated logs in your terminal window.
All of this means the app started processing data.

### Application Internals 

com.tomtom.ingestion.pipeline.DataPipeline class describes our simple flow of processing dynamic data events:

```
public class DataPipeline implements Runnable {

  ...

  public void run() {
    // poll the EIPA API
    poller.stream()
        // adapt to internal models
        .map(adapter)
        // filter out not-changed events
        .filter(filter)
        // send to the down-stream API
        .forEach(sink);
  }
}
```

com.tomtom.ingestion.poller.DynamicDataApiPoller is the very first step of the flow.
It fetches dynamic data API and produces events stream: 


```
public class DynamicDataApiPoller {

  ...

  public Stream<EipaData> stream() {
    logger.debug("Fetching dynamic data API...");

    EipaDataResponse dynamicDataApiResponse = dynamicDataApiClient.getDynamicData();
    String generatedTs = dynamicDataApiResponse.getGenerated();
    logger.debug("Fetched dynamic data response, generatedTs = {}", generatedTs);

    return dynamicDataApiResponse.getData().stream().peek(d -> d.setGenerated(generatedTs));
  }
}

```

com.tomtom.ingestion.processor.DynamicDataApiAdapter adapts remote API data model to our internal one:

```
public class DynamicDataApiAdapter implements Function<EipaData, DynamicDataEvent> {
  ...
    
  @Override
  public DynamicDataEvent apply(EipaData remoteData) {
    logger.trace("Adapting eiapData for pointId = {}..", remoteData.getPointId());

    String pointId = remoteData.getPointId();
    ZonedDateTime originalTs = ZonedDateTime.parse(remoteData.getGenerated());

    // adapt status
    Status status = adapt(remoteData.getStatus());

    return DynamicDataEvent.valueOf(
        EventData.valueOf(pointId, status, originalTs)
    );
  }

}

```

And finally com.tomtom.ingestion.processor.DynamicDataApiFilter with help of redis and very straightforward 
comparison filters out events that were not changed: 


```
public class DynamicDataApiFilter implements Predicate<DynamicDataEvent> {
  ...

  @Override
  public boolean test(DynamicDataEvent newEvent) {
    logger.trace("Looking up for event data for pointId = {}", newEvent.getPointId());
    EventData existingEventData = storage.findById(newEvent.getPointId()).orElse(null);

    logger.trace("Persisting event data for pointId = {}", newEvent.getPointId());
    EventData newEventData = newEvent.getData();
    storage.save(newEvent.getData());

    boolean equal = Objects.equals(newEventData, existingEventData);
    if (equal) {
      logger.debug(
          "Skipping event for point_id = {}. Status {} did not change",
          newEventData.getPointId(), newEventData.getStatus());
    }

    return !equal;
  }
}

```


## License


MIT License

Copyright (c) 2021 Nick

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
