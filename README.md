In this project I wanted to create a kind of advanced RecyclerView wich incorporates a child RecyclerView that is Horizontal (which I didn't do before) but it works after all. I thought about another solution as well which is to populate the RecyclerView with different types of Layouts which I have more experience in it because I did such project structure before. But here is a smartest or shortest solution to launch the populate the second RecyclerView in the onBinding() in the Adapter of the first RecyclerView…

I did my research about the best Image loading Libraries, so I learned a lot of theory about Glide behavior to manage memory. I realize it is an amazing Library after learning what is does behind the scenes. That’s why I would like to share this extract of analysis about it.

In this small project I did an architecture that can incorporate later new classes or features by making a data provider class and architecture for adapters and models and Retrofit singleton and the service Interface that allows us to write more Http requests calls 

For Reactive programming I used Subjects, precisely ReplaySubject as data provider because it acts as Observable and Observer, imagine later in the future some other variables should affect the data provider than this Subject can observe an Observable Data and then emit to its own Observer new data.

I could have used Coroutines Channel send() and receive() when the Retrofit respond successfully but the behavior will be like EventBus, or the data is consumed once then not accessed anymore which is not the case with RelaySubject that emits the last Item that was emitted and the previous Items when an Observer subscribes to it. In my case the Observer was in the ResultActivity that is showing the data.

Returning to Glide, it saved many tasks for me because apparently Glide is able to calculate the number of Pixels required for an ImageView and then not load everything from the source Url. 

Also Glide Cancel the loading Tasks for other Images that are not shown if in case the loading of them is running.  he major reason of slow loading is that we do not cancel the task like downloading, decoding a bitmap even when the view is out of the screen

Glide also creates memory cash for Images. An important  way is to create memory cache so that we do not have to decode the image again and again as decoding takes time. Glide Library creates a cache of some configurable size to catch the bitmaps.

Glide Library maintains 2 level of caching:
> Memory Cache
> Disk Cache

And they work together to avoid loading the same pixels again and reuse the Pixels when needed…

So some Screenshots:


Login

![Screenshot_1589781327](https://user-images.githubusercontent.com/20923486/82181504-f04f9680-98e2-11ea-82a8-550d2c98bcf9.png)

Loggin different types of exceptions

![Screenshot_1589781338](https://user-images.githubusercontent.com/20923486/82181671-43294e00-98e3-11ea-86b5-c6d441604c0e.png)

Results page

![Screenshot_1589783279](https://user-images.githubusercontent.com/20923486/82181911-b4690100-98e3-11ea-955c-aaa95bc84539.png)

Swipe to refresh and update RxJava 6 Ui

![Screenshot_1589784883](https://user-images.githubusercontent.com/20923486/82182923-5ccb9500-98e5-11ea-913d-0ae7ee4bd0ce.png)

Inner Horizontal RecyclerView to parent RecyclerView for Image gallery using Glide Library

![Screenshot_1589784524](https://user-images.githubusercontent.com/20923486/82182339-6f919a00-98e4-11ea-824e-28e4ff4def6b.png)

Full screen Image using Glide

![Screenshot_1589784353](https://user-images.githubusercontent.com/20923486/82182665-e6c72e00-98e4-11ea-88aa-036467e5041f.png)






