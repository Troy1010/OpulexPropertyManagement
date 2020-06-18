package com.example.opulexpropertymanagement.repo

import android.content.Context
import com.example.opulexpropertymanagement.app.App
import com.example.opulexpropertymanagement.app.Config
import com.example.opulexpropertymanagement.models.UserType
import com.example.opulexpropertymanagement.ui.User


object SharedPref {

    val instance = App.getSharedPreferences(
        Config.SHARED_PREF_FILE_NAME,
        Context.MODE_PRIVATE
    )
    val editor = instance.edit()

    fun getUserFromSharedPref(): User? {
        // get User from SharedPref, and feed it into loginAttemptStream
        val storedEmail = SharedPref.instance.getString(Config.KEY_EMAIL, null)
        val storedUserType = SharedPref.instance.getString(Config.KEY_USER_TYPE, null)
        val storedAppApiKey = SharedPref.instance.getString(Config.KEY_APP_API_KEY, null)
        val storedID = SharedPref.instance.getString(Config.KEY_ID, null)
        if ((storedID == null) || (storedEmail == null) || (storedAppApiKey==null) || (storedUserType==null)) {
            return null
        } else {
            return User(storedID, "??", storedEmail, storedAppApiKey, storedUserType)
        }
    }

    fun saveUserInSharedPref(user: User?) {
        if (user == null) {
            editor.clear()
        } else {
            editor.putString(Config.KEY_EMAIL, user.email)
            editor.putString(Config.KEY_ID, user.id)
            editor.putString(Config.KEY_APP_API_KEY, user.appapikey)
            editor.putString(Config.KEY_USER_TYPE, user.usertype)
        }
        editor.commit()

    }


}