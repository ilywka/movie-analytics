# Movie analytics

Movie analytics command line tool. Performs following aggregations over IMDB data:

1. Movie release dynamics for sci-fi, comedy, drama, horror, western movie genres
    in USA and China 2017-2019. 
    - Movie included if its release date for country is present on movie releases page 
    (https://www.imdb.com/title/tt3398314/releaseinfo)
2. Top 10 directors by average movie rating.
   - Directors with movies count >5 included.
   - Director included if he/she 'Best Director-Nominated' or 'Best Director-Winning'. (IMDB people search parameter)

## Getting Started
**!Permissions to create directories/files in root folder required!** \
Start app \
(estimated duration: 1. ~1min 2. ~1min):

`./gradlew clean shadowJar && java -jar build/libs/movie-analytics-1.0-all.jar`  

Results stored in /charts directory.
