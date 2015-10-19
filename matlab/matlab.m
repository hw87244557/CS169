audiodata = csvread('outputfile.csv');
player = audioplayer(audiodata, 44100);
playblocking(player);
