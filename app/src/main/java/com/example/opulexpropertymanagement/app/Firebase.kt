package com.example.opulexpropertymanagement.app

import com.google.firebase.database.FirebaseDatabase


val firebaseDB = FirebaseDatabase.getInstance()
val propertyFBTable = firebaseDB.getReference("PropertyTable")