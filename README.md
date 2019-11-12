# Movie analytics

Movie analytics command line tool. Performs following aggregations over IMDB data:

1. Movie release dynamics for sci-fi, comedy, drama, horror, western movie genres
    in USA and China 2017-2019. 
    - Movie included if it's release date for country is present on movie releases page 
    (https://www.imdb.com/title/tt3398314/releaseinfo)
2. Top 10 directors by average movie rating.
   - Directors with movies count > included.

## Getting Started
Start app \
(estimated duration: 1.~1min 2. ~10min):

`./gradlew clean shadowJar && java -jar build/libs/movie-analytics-1.0-all.jar`  
