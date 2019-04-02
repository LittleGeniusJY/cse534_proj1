also, read the imagefile in the D:// .
in the 222 line , "File imagefile = new File("D://t3.jpg");

when want to change the bitdepth, need to change two line


in the 199 line, if you want to gain a 8bit-depth, make n==7
                         4bit---->n==3
                         2bit----->n==1               
                         1bit----->n==0

in the 190 line,  " int color_catagroy = 256;"
                        8bit-depth------>color_catagroy = 256
                        4----------------->16
                        2----------------->4
                        1----------------->2

the outputfile's path is "D:\\Out.jpg"