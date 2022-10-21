# OakCoding

01418211 Software Construction ปีการศึกษา 2565 ภาคต้น

สร้าง Desktop Application สําหรับการแจ้งเรื่องร้องเรียนของนิสิต  
มหาวิทยาลัยเกษตรศาสตร์ ด้วย JavaFX (JavaSE 17 เท่าน้ัน)  
โดยต้องออกแบบและเขียนโปรแกรมท่ีใช้หลักการโปรแกรมเชิงวัตถุ

<br />

### เริ่มต้น

1. **Clone this Repository**

        $ git clone https://github.com/CS211-651/project211-oakcoding.git

1. **Change Directory into the Project Directory**

        $ cd project211-oakcoding

1. **Execute the Application**

        $ sh run.sh

<br />

### ความก้าวหน้าของระบบ

| ครั้งที่ | วันที่        | ความก้าวหน้าของระบบ |
| :--: | :--------: | :---------------- |
| 1    | 2022-08-11 | ร่าง User Interface, mockup หน้า Sign In ของแอปพลิเคชัน, patch FXRouter, เพิ่ม custom font ผ่าน CSS, ทดลอง implement module สำหรับอ่าน configuration file |
| 2    | 2022-09-08 | Class สำหรับอ่าน/เขียนข้อมูลลงบนไฟล์, UI improvements, configuration class, message digest class |
| 3    | 2022-09-30 | UI design, FXML controller linking, dynamic database, StageManager (window management) |
| 4    | 2022-10-21 | UI implementation, User, Department, and Issue system |

</br >

### คำอธิบาย Directory

```
    data/               ->      Data source directory (created later by the app itself)
    docs/               ->      Documentation resources such as images and diagrams
    src/main/           ->      Application source code and resources
        java/           ->      Application module root
        resources/      ->      Application resources
            css/        ->      Cascading Stylesheets
            default/    ->      Default resource
            fonts/      ->      Font resources
            fxml/       ->      FXML resources
            images/     ->      Images and icons
            indices/    ->      StageManager index file for automatic FXML loading and linking
```

</br >

### รายชื่อสมาชิก

| ลําดับ | ชื่อ                     | นามสกุล                               | GitHub Username                                               | รหัสนิสิต       | KU E-mail Address |
| :--: | :--------------------- | :----------------------------------- | :------------------------------------------------             | :---------   | :---------------- |
| 1    | ปาณชัย<br />Panachai    | คชกาษร<br />Kotchagason              | [@ingfosbreak](https://github.com/ingfosbreak)                | `6410450176` | panachai.ko@ku.th |
| 2    | ธนากร<br />Thanakorn   | คนหมั่น<br />Khonman                   | [@Thanakorn0Khonman](https://github.com/Thanakorn0Khonman)    | `6410451041` | thanakorn.khon@ku.th |
| 3    | ธเนศ<br />Thanet       | จีนสีคง<br />Jinseekhong               | [@thanetjin](https://github.com/thanetjin)                    | `6410451067` | thanet.jin@ku.th  |
| 4    | พศวัต<br />Potsawat     | ถิ่นกาญจน์วัฒนา<br />Thinkanwatthana     | [@naiithink](https://github.com/naiithink)                    | `6410451199` | potsawat.t@ku.th  |
