package com.example.thanhhai.banmuonhenho;

public class UserLooking {
    Integer relationship;
    Integer sexuality;
    Integer height;
    Integer weight;
    Integer bodyImage;
    Integer eyeColor;
    Integer hairColor;
    Integer living;
    Integer drinking;
    Integer smoking;
    Integer kid;

    public UserLooking() {
        this.relationship = 0;
        this.sexuality = 0;
        this.height = 0;
        this.weight = 0;
        this.bodyImage = 0;
        this.eyeColor = 0;
        this.hairColor = 0;
        this.living = 0;
        this.drinking = 0;
        this.smoking = 0;
        this.kid = 0;
    }

    public UserLooking(Integer relationship, Integer sexuality, Integer height, Integer weight,
                       Integer bodyImage, Integer eyeColor, Integer hairColor, Integer living, Integer drinking, Integer smoking, Integer kid) {
        this.relationship = relationship;
        this.sexuality = sexuality;
        this.height = height;
        this.weight = weight;
        this.bodyImage = bodyImage;
        this.eyeColor = eyeColor;
        this.hairColor = hairColor;
        this.living = living;
        this.drinking = drinking;
        this.smoking = smoking;
        this.kid = kid;
    }


    String getString(){

        String[] bodyImageString= {"Chưa trả lời","Bình thường","Cơ bắp","Gầy","Mập","Thon gọn"};
        String[] drinkingString= {"Chưa trả lời","Tôi không uống rượu","Tôi uống rượu ít","Tôi uống rượu thường xuyên"};
        String[] eydColorString= {"Chưa trả lời","Đen","Xanh dương","Xanh lá","Nâu","Nâu đỏ","xám"};
        String[] hairColorString= {"Chưa trả lời","Đen","Xanh dương","Xanh lá","Nâu","Nâu đỏ","xám","Trắng","Tím","Vàng"};
        String[] heightString= {"Chưa trả lời","Nhỏ hơn 150cm","150-155cm",
                "155-160cm","160-165cm","165-170cm","170-175cm","175-180cm","180-185cm","185-190cm","Lớn hơn 190cm"};
        String[] kidString= {"Chưa trả lời","Có con đã lớn","Có con còn nhỏ","Chưa có con","Sắp có con"};
        String[] livingString= {"Chưa trả lời","Sống một mình","Sống với ba mẹ ","Sống ở kí túc xá","Sống với bạn bè"};
        String[] relationshipString= {"Chưa trả lời","Có mối quan hệ phức tạp","Đang độc thân","Đã ly hôn"};
        String[] sexualityString= {"Chưa trả lời","Đồng tính","Dị tính","Song tính"};
        String[] smokingString= {"Chưa trả lời","Tôi không hút thuốc","Tôi ít hút thuốc","Tội hút thuốc thường xuyên"};
        String[] weightString= {"Chưa trả lời","Nhỏ hơn 40kg","40-45kg","45-50kg","50-55kg","55-60kg","60-65kg","65-70kg","70-75kg","75-80kg","80-85kg","85-90kg","90-95kg","95-100kg","100-105kg","105-110kg","115-120kg","120-125kg","125-130kg","Lớn hơn 130kg"};

        String a="";

        if(living>0) {
            a += livingString[living];
            a += ", ";
        }

        if(hairColor>0){
            a+= "tóc ";
            a+=hairColorString[hairColor];
            a+=", ";
        }
        if(eyeColor>0){
            a+= "mắt ";
            a+=eydColorString[eyeColor];
            a+=", ";
        }

        if(height>0){
            a+= "cao ";
            a+=heightString[height];
            a+=", ";
        }

        if(weight>0){
            a+= "nặng ";
            a+= weightString[weight];
            a+=", ";
        }

        if (smoking>0){
            a+= smokingString[smoking];
            a+=", ";
        }

        if(kid>0) {
            a += kidString[kid];
            a += ", ";
        }

        if(relationship>0) {
            a += relationshipString[relationship];
            a+=", ";
        }
        if(sexuality>0){
            a+=sexualityString[sexuality];
            a+=", ";
        }
        if(drinking>0){
            a+=drinkingString[drinking];
            a+=", ";
        }
        if(bodyImage>0){
            a+="Ngoại hình ";
            a+= bodyImageString[bodyImage];

        }

        return a;
    }


}

