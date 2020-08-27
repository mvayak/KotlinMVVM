package co.com.test.view

import android.app.Application
import co.com.test.di.mainAppModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

/**
 * @desc this is application class and this class use for start koin module
 * @author : Mahesh Vayak
 * @required
 **/
class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            // Android context
            androidContext(this@BaseApplication)
            // modules
            modules(mainAppModules)

        }

    }
}