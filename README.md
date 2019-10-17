# Proof-of-concept
Proof of concept is an android proficiency exercise

# Task Specifications covered

Created an Android app which contains:
- [x] Ingests a json feed from [https://dl.dropboxusercontent.com/s/2iodh4vg0eortkl/facts.json](https://dl.dropboxusercontent.com/s/2iodh4vg0eortkl/facts.json)

    • The feed contains a title and a list of rows.
     
    • Use of third party json parser to parse this if desired.

- [x] Display the content *(including image, title and description)* in a RecyclerView

    • The title in the ActionBar should be updated from the json data.
 
    • Each row should be dynamically sized to the right height to display its content - no clipping, no extraneous white-space etc. This means some rows will be larger than others.

- [x] Loads the images lazily *(does not download them all at once, but only as needed)*.

- [x] Implement a refresh function allowing the data & view to be updated

    • Use of pull down to refresh.

- [x] Does not block UI when loading the data from the json feed.


# Project Structure
• MVVM Architecture with Databinding and  AndroidX 

• Kotlin for programming language

• Repository pattern for handling business logic

• Binder pattern used for all activities

• Builder pattern used for Recylerview 

• Kotlin DSL implementation

• Kotlin Extensions 



# Open source libraries used
1 . Gson  for Json parsing

2 . Retrofit for Network HTTP calls 

3 . Glide for image processing 

4 . Room persistence for sqlite database integration

5 . SwipeRefreshlayout for refresh data component


# Project Screenshots

• In portrait mode

![](https://i.stack.imgur.com/vhIUV.png)

• In Landscape mode  ( Staggered Layout)

![](https://i.stack.imgur.com/57Hdz.png)

# Output

Download Apk from **[here](https://github.com/nirav-joshi/Proof-of-concept/raw/master/APK/Proof_Of_Concept-1.0.apk)**