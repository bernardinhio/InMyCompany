In this project I wanted to create a kind of advanced RecyclerView that incorporates a child RecyclerView which is Horizontal. I didn’t do such RecyclerView before. Originally I thought about to populate the RecyclerView with different types of Layouts: One for Image gallery Layout and another for other types of information and views. I did such structure of RecyclerView before to put different types of items inside it.

But since in my case some items have an Image Library and some not, I thought about making inside each item of the parent RecyclerView  an inner Horizontal RecyclerView that will show the Images by scrolling Horizontally.  Here is a smartest or shortest solution to launch the populate of the second RecyclerView in the onBinding() method of the Adapter of the first RecyclerView.

To load Images from Urls, I did my research about the best Image loading Libraries. I know Picasso for example but it doesn’t really have advanced processing of the data for the loading of large images.  Fresco and Glide Libraries do the advanced memory management because the most common kind of errors for images Apps is the “Out of memory” exception.

So I enjoyed learning a lot of theory about Glide behavior to manage memory. I realize it is an amazing Library after learning what is does in behind the scenes. I will explain later what I learned about it, information I wasn’t aware of because using Glide is so simple but we don’t realize the constraints when dealing with images in a mobile App.

In this small project I did an architecture that can incorporate later new classes or features by making a data provider class used to hold global values accessible by all classes. I did an architecture for adapters and data models and Retrofit singleton and the service Interface that allows us to write in the future more Http requests calls 

For Reactive programming I used Subjects, precisely ReplaySubject as data provider because it acts as Observable and Observer, imagine later in the future in a real App, some other variables should affect the data provider than this Subject can observe an Observable Data and then emit to its own Observer new data.

I could have used Coroutines: Channel send() and receive() when the Retrofit respond successfully but the behavior will be like EventBus, or the data is consumed once then not accessed anymore which is not the case with ReplaySubject that emits the last Item that was emitted and the previous Items whenever an Observer subscribes to it. In my case the Observer was in the ResultsActivity that is showing the data.

I did small Swipe to refresh feature that updates the ReplaySubject of RxJava and then repopulate the RecyclerView with the new version of data.

For the design, I wanted to just make a simple thing of having the Items of the RecyclerView to have different border colors depending on the department of the company. So if it is Engineering, then show border in Orange, if Marketing then show in Purple…ect. Same for a small label on the corner that shows the department name. See in screenshot.

Returning to Glide technology, it saved many tasks for me because apparently Glide is able to calculate the number of Pixels required for an ImageView and then not load everything from the source Url. 

Also Glide cancels the loading Tasks for other Images that are not shown if in case the loading of them is running.  The major reason of slow loading is that we do not cancel the task like downloading, decoding a bitmap even when the view is out of the screen

Glide also creates memory cash for Images. An important way is to create memory cache so that we do not have to decode the image again and again as decoding takes time. Glide Library creates a cache of some configurable size to catch the bitmaps.

Glide Library maintains 2 level of caching:

-> Memory Cache
-> Disk Cache

When we provide the url to the Glide, Glide checks if the image with that url key is available in the memory cache or not

1. If present in the memory cache:
THEN
> It just shows the bitmap by taking it from the memory cache

2. If not present in the memory cache:
THEN
> It checks in the disk cache

3. If present in the disk cache:
THEN
> It loads the bitmap from the disk
THEN
> Puts it in the memory cache
THEN
> Load bitmap in the view

4 If not present in the disk cache:
THEN
> It downloads the image from the network
THEN
> Puts it in the disk cache
THEN
> Puts it in the memory cache
THEN
> Load the bitmap in the view

This way Glide Library makes loading fast because showing directly from the memory cache is faster than the disk cache and the disk cache is faster than loading from the network

And they work together to avoid loading the same pixels again and reuse the Pixels when needed…

In this project, I made small animations in the homepage. I used Retrofit to parse data after I created a mock link using ww.mocky.io to use a Basic Authentication, then I made a

So some Screenshots:


Login and company Logo animation bounce + blinking + Loggin different types of exceptions

![Screenshot_1591123402](https://user-images.githubusercontent.com/20923486/83557724-69b9cc80-a512-11ea-87fc-5cc38fdd9bfc.png)


Results page

![Screenshot_1591123588](https://user-images.githubusercontent.com/20923486/83557949-ba312a00-a512-11ea-87ad-3fbb2512254e.png)


Inner Horizontal RecyclerView to parent RecyclerView for Image gallery using Glide Library

![Screenshot_1591123651](https://user-images.githubusercontent.com/20923486/83558193-f1074000-a512-11ea-864f-09b0a460e6e5.png)


Swipe to refresh and update RxJava 6 Ui

![Screenshot_1591123875](https://user-images.githubusercontent.com/20923486/83558122-d46b0800-a512-11ea-9009-f5d2bc79ea0e.png)


Full screen Image using Glide

![Screenshot_1589784353](https://user-images.githubusercontent.com/20923486/82182665-e6c72e00-98e4-11ea-88aa-036467e5041f.png)

