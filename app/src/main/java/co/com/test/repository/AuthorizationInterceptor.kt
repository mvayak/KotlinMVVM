package co.com.test.repository

import okhttp3.Interceptor
import okhttp3.Response
import org.koin.core.KoinComponent

/**
 * @desc this is class use for pass Authorization token in API header
 * @author : Mahesh Vayak
 * @required
 **/
class AuthorizationInterceptor : Interceptor, KoinComponent {
    // Right now passing token as hardcorded
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val builder = request.newBuilder()
        builder.header(
            "Authorization",
            String.format(
                "Bearer %s",
                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1aWQiOiIxMjM0NTY3ODkwIiwiaWRlbnRpdHkiOiJKb2huIERvZSJ9.Faij1Chh1CdrBqLZLv9MW-BL10WTxIbi_0hIGjt3MFo"
            ))
        request = builder.build() //overwrite old request
        return chain.proceed(request)
    }


}