audiodata1 = csvread('outputfile1.csv');
audiodata2 = csvread('outputfile2.csv');
% player = audioplayer(audiodata, 44100);
% playblocking(player);
plot(audiodata1);
%plot(audiodata2);
% plot(audiodata2 - audiodata1);