The project contains leak error:
- Run application
- Press "Back" btn to close the app
(Bug) LeakCanary error:
```
2022-04-20 15:05:27.839 21340-21340/com.github.kolya.myapplication D/LeakCanary: ​
    ┬───
    │ GC Root: Global variable in native code
    │
    ├─ android.service.media.MediaBrowserService$ServiceBinder instance
    │    Leaking: UNKNOWN
    │    Retaining 10,1 kB in 96 objects
    │    this$0 instance of androidx.media.MediaBrowserServiceCompat$MediaBrowserServiceImplApi26$MediaBrowserServiceApi26
    │    ↓ MediaBrowserService$ServiceBinder.this$0
    │                                        ~~~~~~
    ├─ androidx.media.MediaBrowserServiceCompat$MediaBrowserServiceImplApi26$MediaBrowserServiceApi26 instance
    │    Leaking: YES (Service not held by ActivityThread)
    │    Retaining 9,6 kB in 95 objects
    │    mBase instance of com.github.kolya.myapplication.service.MusicService
    │    ↓ ContextWrapper.mBase
    ╰→ com.github.kolya.myapplication.service.MusicService instance
    ​     Leaking: YES (ObjectWatcher was watching this because com.github.kolya.myapplication.service.MusicService
    ​     received Service#onDestroy() callback and Service not held by ActivityThread)
    ​     Retaining 7,7 kB in 81 objects
    ​     key = 64f61139-e21a-47d3-8759-b1c9c5be6d83
    ​     watchDurationMillis = 5199
    ​     retainedDurationMillis = 178
    ​     mApplication instance of android.app.Application
    ​     mBase instance of android.app.ContextImpl

    METADATA

    Build.VERSION.SDK_INT: 29
    Build.MANUFACTURER: Google
    LeakCanary version: 2.8.1
    App process name: com.github.kolya.myapplication
    Stats: LruCache[maxSize=3000,hits=34152,misses=85683,hitRate=28%]
    RandomAccess[bytes=3961419,reads=85683,travel=23994363586,range=16504510,size=21876036]
    Analysis duration: 3061 ms

```