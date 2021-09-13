package com.kenshi.newsapiclient.data.db

import androidx.room.TypeConverter
import com.kenshi.newsapiclient.data.model.Source

//problem
//sources property(Source Object type) in Article class represent another data class
//for situation like this. when we have object reference variables other than strings in an entity class
//we have two option
//1. simplest but inefficient solution is annotating this source class also as an entity class and saving source
//data to a separate data table but this source class only have two properties(id, name)
//All we want from it is displaying the name of source, so there is no need to create separate table for source data

//2. create converter class for source data using room type converter annotation
//for the database related components create a new package and name it as db
//now, inside the db package create a new class and name it as converters
//define function to return the name of the source instead of Source object

//한마디로 source entity 안만들고 source의 name만 뽑아오는 converter 클래스 만들어서
class Converters {

    //room will use this function and only save the name of the resource to the articles data.
    @TypeConverter
    fun fromSource(source: Source):String{
        return source.name
    }

    //function to get an source instance from the room table when retrieving data
    //we are not going to use the resource id, it will not be a problem
    @TypeConverter
    fun toSource(name: String):Source{
        return Source(name,name)
    }
}