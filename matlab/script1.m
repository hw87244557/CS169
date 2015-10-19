audiodata1 = csvread('./test4/outputfile1.csv');
audiodata2 = csvread('./test4/outputfile2.csv');
audiodata3 = csvread('./test4/outputfile3.csv');
audiodata4 = csvread('./test4/outputfile4.csv');
audiodata5 = csvread('./test4/outputfile5.csv');
audiodata6 = csvread('./test4/outputfile6.csv');
audio_1 = (audiodata1+audiodata2+audiodata3) / 3;
audio_2 = (audiodata4+audiodata5+audiodata6) / 3;
compare(audio_1,audio_2);