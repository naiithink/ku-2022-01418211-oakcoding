# oakcoding/info: workflow

## Branching

```
  about-us              ข้อมูลเกี่ยวกับ team เรา
* develop               สำหรับทำงาน
  freeze                snapshot ของ working tree ก่อนเริ่มทำงาน
  info                  เอกสาร และอื่น ๆ
  main                  (merge / pull request เท่านั้น) เปรียบเสมือนบน GitHub Classroom
                        ห้ามแก้ไขไฟล์ในนี้โดยตรง
```

## Flow

```
                                        naiithink/oakcoding.git               #                  CS211-651/project211-oakcoding.git
                                                                              #
                                        :                  :                  #                  :
                                        |                  |                  #                  |
                                        develop            main               #                  main
                                        |                  |                  #                  |
                                        |                  |                  #                  |
                                        |                  |                  #                  |
* dev dev dev                           * C11              |                  #                  |
                                        |                  |                  #                  |
* dev dev dev                           * C12              |                  #                  |
                                        |                  |                  #                  |
* dev dev dev                           * C13              |                  #                  |
                                        |                  |                  #                  |
* dev dev dev                           * C14              |                  #                  |
                                        |\                 |                  #                  |
                                        | \                |                  #                  |
* preparing to submit                   |   --- merge ---> * C15              #                  |
  or finished some feature              |                  |                  #                  |
                                        |                  |                  #                  |
                                        |                  |                  #                  |
| everyone reviews changes              |                  |                  #                  |
  to be submitted                       |                  |                  #                  |
                                        |                  |                  #                  |
                                        |                  |                  #                  |
+ project release                       |                  + ------ push -----=----------------> * C15
  or submitting progress update         |                  |                  #                  |
                                        |                  |                  #                  |
                                        |                  |                  #                  |
                                        |                  |                  #                  |
                                        :                  :                  #                  :
```

`Cxx`: `C`ommit
