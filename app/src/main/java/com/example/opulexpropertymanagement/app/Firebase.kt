package com.example.opulexpropertymanagement.app

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference


val firebaseDB = FirebaseDatabase.getInstance()
var fbUserDBTable: DatabaseReference? = null

val firebaseStorage = FirebaseStorage.getInstance()
var fbUserStorageTable: StorageReference? = null