namespace java com.test.thrift.demo;
struct News{
    1:i32 id;
    2:string title;
    3:string content;
    4:string mediaFrom;
    5:string author;
}
service indexNewsOperatorSevices{
    bool indexNews(1:News indexNews);
    bool removeNewsById(1:i32 id);
}

struct People{
1:string name;
2:i32 age;
3:string gender;
}