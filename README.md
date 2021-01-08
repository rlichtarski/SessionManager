# SessionManager
Faking login/register app using [Reqres](https://reqres.in/) REST API with auto-re-authentication when the app launches. The app uses MVVM architecture with Jetpack (Room, ViewModel, LiveData, DataStore, View Binding), Hilt, Coroutines, Flows, etc...

# Project features
This app uses some modern Android tech stacks & libraries:
- 100% [Kotlin](https://kotlinlang.org/) + [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) + [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/) for asynchronus
- [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) - for dependency injection.
- [Retrofit2](https://github.com/square/retrofit) - A type-safe HTTP client for Android.
- [Moshi](https://github.com/square/moshi) - a modern JSON library for Android. 
- [JetPack](https://developer.android.com/jetpack):
  - [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) - observable data holder class.
  - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - store and manage UI-related data in a lifecycle conscious way.
  - [Room Persistence](https://developer.android.com/training/data-storage/room) - local database.
  - [ViewBinding](https://developer.android.com/topic/libraries/view-binding) - interact with views (replacement for findViewById).
  - [DataStore](https://developer.android.com/topic/libraries/architecture/datastore) - replacement for SharedPreferences - allows to store key-value pairs. 
- Architecture: 
  - MVVM Architecture 
  - Repository pattern with use-cases.
  - Mapping local entities and remote data transfer objects (DTOs) into Domain Model.
- [Timber](https://github.com/JakeWharton/timber) - logging.

# Open API (Reqres)
SessionManager uses the [Reqres](https://reqres.in/) REST API for faking login/registration and receiving an auth token. Reqres simulates real application scenarios. If you want to test a user authentication system, Reqres will respond to a successful login/register request with a token for you to identify a sample user, or with a 403 forbidden response to an unsuccessful login/registration attempt. With Reqres you can only login/register with predefined users.

# How does auto-authentication work?
When a user successfully logs in or registers, the user's email gets saved into the DataStore (the key-value email denotes what the previously authenticated user was). When the app opens the next time, the first thing that is checked is this email key-value. Then the email is used to query the token from the Room Persistence library. </br> If the token is found, the user is automatically authenticated. If the token isn't found, the user won't be authenticated. </br>
When the user logs out, the token is deleted from the database, so even though the email is still in the DataStore, the token will never be found, so the user won't be authenticated

![](https://github.com/rradzzio/SessionManager/blob/main/example.gif)
