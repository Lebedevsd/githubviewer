# github viewer

This is show case project:
You can search for the repositories and by clicking on each repository you can review contributors of that repository.

Api that was used is https://api.github.com/
There are some limitations as 60 requests per hour for unautorised use.

To build release please execute: `./gradlew assembleRelease`

To deploy release version of the application please run `adb install app/build/outputs/apk/release/app-release.apk` from pleject directory.

It will use release_key, note *NEVER* put your own release keys under version control.

Start screen:
![Start screen](screenchots/start.png)

Active Search:
![Active Search](screenchots/search.png)

Contributors screen:
![Contributors screen](screenchots/contributors.png)

##Libraries in use:
* ViewModel
* DataBinding
* RxJava
* Dagger
* Assisted inject
* Retrofit
* Mochi
* Glide
* Epoxy
* Navigation
* Timber
