# NASA_APOD

About the Application : NASA_APOD is an Android App that lets users view the NASA's Astronomy Picture of the Day (APOD). Each day a different image or photograph of our fascinating universe is featured, along with a brief explanation written by a professional astronomer.

Architecture used for building NASA APOD App -> MVVM (Model View ViewModel)
• **Model**: The model represents the data and the business logic of the application. It consists of the model classes created for the response received from SpaceX API for AllLaunches, OneRocket and OneLaunch APIs.

• **View**: The view is the UI Code for activities. It sends the user action to the ViewModel but does not get the response directly. There are 3 activities in this project. The activities subscribe to the observables which is being exposed by the ViewModel and when it starts receiving the data, it will set to the UI views. To display the list of all the data, the app uses Recycler view with an adapter. The adapter has two types of view holders i.e, one for the 'Header' and the other for the 'Content' under that heading. The Header view is only visible when there is some grouping operation done.

• **ViewModel**: The ViewModels are a bridge between the View and the Model. It does not have any direct reference of the view. It interacts with the Model and exposes the observable that can be observed by the View. The SpaceX app has two viewmodels : one for the main screen that fetches all launches data and the other for the details screen that fetches one rocket and one launch data. A lifecycle scope is defined for each Lifecycle object. Here it is the activity scope. As we are using Dispatchers.IO, the coroutine will start in the IO thread which will be responsible for the network operation. The result of this function call gets emitted to a livedata, which will then be observed by the view.

Library used for networking -> **Retrofit** with **coroutine** support

Using kotlin Suspend function as it helps the coroutines to pause, perform the required job on the IO thread, wait for the response, and then resume by emitting the respective value that is obtained from the network call

Using **Constraint Layouts** to support different screen sizes.

To support network unavailability, using **Room DB** to store response fetched from API call to the SQLite DB.

Functionalities suppported for Users :
1. Users can view APOD for today's date when app is launched.
2. If they want to search for a picture of the date of their choice, the calendar option is available to do so.
3. The users can also mark and view their favorite APODs.
4. To optimize battery usage, users can use the app in dark mode.
5. The app can be viewed in both potrait & landscape orientation and for different screen sizes.


Screenshots for App Screen :
![Screenshot_APOD1](https://user-images.githubusercontent.com/83055905/155932469-6f894890-1874-4b40-aded-a9300959e42c.png)

