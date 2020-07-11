package com.example.opulexpropertymanagement.layers.data_layer

import android.content.Context
import com.example.opulexpropertymanagement.*
import com.example.opulexpropertymanagement.App
import com.example.opulexpropertymanagement.layers.z_ui.User
import javax.inject.Inject

// GlobalVM needs access
class SharedPrefWrapper @Inject constructor(): SharedPrefWrapperInterface {

    val sharedPreferences = App.getSharedPreferences(
        SHARED_PREF_FILE_NAME,
        Context.MODE_PRIVATE
    )
    val editor = sharedPreferences.edit()

    override fun readUser(): User? {
        // get User from SharedPref, and feed it into loginAttemptStream
        val storedEmail = sharedPreferences.getString(KEY_EMAIL, null)
        val storedUserType = sharedPreferences.getString(KEY_USER_TYPE, null)
        val storedAppApiKey = sharedPreferences.getString(KEY_APP_API_KEY, null)
        val storedID = sharedPreferences.getString(KEY_ID, null)
        if ((storedID == null) || (storedEmail == null) || (storedAppApiKey==null) || (storedUserType==null)) {
            return null
        } else {
            return User(
                appapikey = storedAppApiKey,
                msg = "??",
                email = storedEmail,
                id =  storedID,
                usertype = storedUserType
            )
        }
    }

    override fun writeUser(user: User?) {
        if (user == null) {
            editor.clear()
        } else {
            editor.putString(KEY_EMAIL, user.email)
            editor.putString(KEY_ID, user.id)
            editor.putString(KEY_APP_API_KEY, user.appapikey)
            editor.putString(KEY_USER_TYPE, user.usertype)
        }
        editor.commit()

    }
}