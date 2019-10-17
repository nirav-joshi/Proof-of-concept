# Proof-of-concept
Proof of concept is an android proficiency exercise

# Task Specifications covered

Create an Android app which:
1. Ingests a json feed from https://dl.dropboxusercontent.com/s/2iodh4vg0eortkl/facts.json

• The feed contains a title and a list of rows.
 
• You can use a third party json parser to parse this if desired.

2. Display the content (including image, title and description) in a ListView

• The title in the ActionBar should be updated from the json data.
 
• Each row should be dynamically sized to the right height to display its content - no clipping, no extraneous white-space etc. This means some rows will be larger than others.

3. Loads the images lazily (don’t download them all at once, but only as needed).

4. Implement a refresh function allowing the data & view to be updated

• Use either a refresh button or pull down to refresh.

5. Should not block UI when loading the data from the json feed.

6. Each row of the table should look roughly like the following image:


# Project Structure
MVVM Architecture with Databinding and  AndroidX 

Kotlin for programming language

Binder pattern used for all activities

Builder pattern used for Recylerview 

Kotlin Dsl implementation

Kotlin Extensions 



# Open source libraries used
1 . Gson  for Json parsing

2 . Retrofit for Network HTTP calls 

3 . Glide for image processing 


4 . Room persistence for sqlite database integration

5 . SwipeRefreshlayout for refresh data component


