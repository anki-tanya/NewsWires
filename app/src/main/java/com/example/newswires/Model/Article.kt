package com.example.newswires.Model

class Article {
    var source: MutableList<IdName>?=null
    var author:String?= null
    var title:String?= null
    var description:String?= null
    var url:String?= null
    var urlToImage:String?= null
    var publishedAt:String?= null
    var content:String?=null
}

class IdName {
    var id : String ? = null
    var name: String?=null
}
