package com.example.opulexpropertymanagement.layers.data_layer

import javax.inject.Inject

class Repo @Inject constructor(
    private val sharedPrefWrapper: SharedPrefWrapper,
    private val documentDataSource: DocumentDataSource
) : ISharedPrefWrapper by sharedPrefWrapper,
    IDocumentDataSource by documentDataSource
