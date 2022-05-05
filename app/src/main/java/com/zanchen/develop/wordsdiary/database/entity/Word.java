package com.zanchen.develop.wordsdiary.database.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "words", indices = {@Index(value = {"words_name"},unique = true)})
public class Word {
    //单词编号
    @PrimaryKey(autoGenerate = true)
    public int _id;
    //单词名
    @ColumnInfo(name = "words_name")
    public String words_name;
    //词性
    @ColumnInfo(name = "words_parts")
    public String words_parts;
    //中文含义
    @ColumnInfo(name = "words_meanCN")
    public String words_meanCN;
    //英文含义
    @ColumnInfo(name = "words_meanEN")
    public String words_meanEN;
    //美式音标
    @ColumnInfo(name = "words_phoneticUS")
    public String words_phoneticUS;
    //英式音标
    @ColumnInfo(name = "words_phoneticUK")
    public String words_phoneticUK;
    //美式发音
    @ColumnInfo(name = "words_pronounceUS")
    public String words_pronounceUS;
    //英式发音
    @ColumnInfo(name = "words_pronounceUK")
    public String words_pronounceUK;
    //过去式
    @ColumnInfo(name = "words_tense")
    public String words_pastTense;
    //过去分词
    @ColumnInfo(name = "words_tense")
    public String words_pastParticiples;
    //近义词
    @ColumnInfo(name = "words_synonym")
    public String words_synonym;
    //词组
    @ColumnInfo(name = "words_phrases")
    public String words_phrases;
    //例句
    @ColumnInfo(name = "words_sentences")
    public String words_sentences;

    public Word(){
        super();
    }


}
