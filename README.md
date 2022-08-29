# OakCoding

01418211 Software Construction ปีการศึกษา 2565 ภาคต้น

สร้าง Desktop Application สําหรับการแจ้งเรื่องร้องเรียนของนิสิต  
มหาวิทยาลัยเกษตรศาสตร์ ด้วย JavaFX (JavaSE 17 เท่าน้ัน)  
โดยต้องออกแบบและเขียนโปรแกรมท่ีใช้หลักการโปรแกรมเชิงวัตถุ

<br />

### เริ่มต้น

1. **Executable File**

    วิธีได้มาซึ่ง executable file ของ application

    **วิธีที่ 1** – Build ด้วย Maven (แนะนำ[^1])  

        $ mvn clean package
    **วิธีที่ 2** – Clone git repository นี้[^2]  

    Executable file และ/หรือ script file ที่เกี่ยวของกับการ execute application ของ project นี้ถูกเก็บอยู่ที่ [`submit/app/`](submit/app/) ของ repository นี้

2. **Execute the Application**[^1] [^2]

    เริ่มการทำงานของ application

    **UNIX** – macOS, GNU/Linux  

        $ java -jar "$(ls submit/app/*shaded.jar)"

    **Windows (PowerShell)**

        > .\submit\app\*shaded.jar

</br >

### ความก้าวหน้าของระบบ

| ครั้งที่ | วันที่        | ความก้าวหน้าของระบบ |
| :--: | :--------: | :---------------- |
| 1    | 2022-08-11 | ร่าง User Interface, mockup หน้า Sign In ของแอปพลิเคชัน, patch FXRouter, เพิ่ม custom font ผ่าน CSS, ทดลอง implement module สำหรับอ่าน configuration file |

</br >

### รายชื่อสมาชิก

| ลําดับ | ชื่อ                     | นามสกุล                               | GitHub Username                                               | รหัสนิสิต       | KU E-mail Address |
| :--: | :--------------------- | :----------------------------------- | :------------------------------------------------             | :---------   | :---------------- |
| 1    | ปาณชัย<br />Panachai    | คชกาษร<br />Kotchagason              | [@ingfosbreak](https://github.com/ingfosbreak)                | `6410450176` | panachai.ko@ku.th |
| 2    | ธนากร<br />Thanakorn   | คนหมั่น<br />Khonman                   | [@Thanakorn0Khonman](https://github.com/Thanakorn0Khonman)    | `6410451041` | thanakorn.khon@ku.th |
| 3    | ธเนศ<br />Thanet       | จีนสีคง<br />Jinseekhong               | [@thanetjin](https://github.com/thanetjin)                    | `6410451067` | thanet.jin@ku.th  |
| 4    | พศวัต<br />Potsawat     | ถิ่นกาญจน์วัฒนา<br />Thinkanwatthana     | [@naiithink](https://github.com/naiithink)                    | `6410451199` | potsawat.t@ku.th  |

### ความก้าวหน้าของระบบ

| ครั้งที่ | วันที่        | ความก้าวหน้าของระบบ |
| :--: | :--------: | :---------------- |
| 1    | 2022-08-11 | ร่าง User Interface, mockup หน้า Sign In ของแอปพลิเคชัน, patch FXRouter, เพิ่ม custom font ผ่าน CSS, ทดลอง implement module สำหรับอ่าน configuration file |
