# OakCoding

01418211 Software Construction ปีการศึกษา 2565 ภาคต้น

สร้าง Desktop Application สําหรับการแจ้งเรื่องร้องเรียนของนิสิต  
มหาวิทยาลัยเกษตรศาสตร์ ด้วย JavaFX (JavaSE 17 เท่าน้ัน)  
โดยต้องออกแบบและเขียนโปรแกรมท่ีใช้หลักการโปรแกรมเชิงวัตถุ

<br />

### เริ่มต้น

#### Executable File

วิธีได้มาซึ่ง executable file ของ applications

* **วิธีที่ 1** – Build ด้วย Maven (แนะนำ[^1])  

        $ mvn clean package
* **วิธีที่ 2** – Clone git repository นี้[^2]  
    Executable file และ/หรือ script file ที่เกี่ยวของกับการ execute application ของ project นี้ถูกเก็บอยู่ที่ [`submit/app/`](submit/app/) ของ repository นี้

#### Execute the Application

* **UNIX** – macOS, GNU/Linux  

        $ java -jar "$(ls submit/app/*shaded*)"

* **Windows**

        > 

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

</br >

### คำอธิบาย Directory

| Directory | คำอธิบาย |
| :-------- | :------ |
| [`submit/app/`](submit/app/) | executable file ของ application และ/หรือ script file ที่เกี่ยวของกับการ execute application ของ project |
| [`submit/diagrams/`](submit/diagrams/) | image file ของ diagram ต่าง ๆ ที่เกี่ยวข้องกับ project |


<br />

<!-- Footer -->

[^1]: ใน project นี้จำเป็นจะต้องใช้ JavaFX, บาง module ของ JavaFX มีความจำเพาะต่อ runtime OS จึงอาจทำให้ JAR file ที่พวกเรา (ผู้จัดทำ) ได้ build ไว้ใน [`submit/app/`](submit/app/) ไม่สามารถ execute บน OS ของผู้ใช้ได้ พวกเราจึงแนะนำให้ build executable file
    ของ project นี้ด้วยอุปกรณ์ที่ผู้ใช้จะใช้ในการ execute application
[^2]: พวกเรา (ผู้จัดทำ) ได้พยายามแก้ปัญหาเรื่องความจำเพาะของ runtime OS ด้วยการจัดการ dependency ใน JAR file แต่พวกเราไม่สามารถรับประกันได้ว่า JAR file ที่อยู่ใน [`submit/app/`](submit/app/) นั้นจะสามารถ execute ได้บนทุก OS (macOS, Windows, GNU/Linux)
    เนื่องจากอาจเกิดปัญหา collision ภายใน dependency พวกเราจึงแนะนำให้ผู้ใช้ build executable file ของ project นี้ด้วยอุปกรณ์ที่ผู้ใช้จะใช้ในการ execute application
