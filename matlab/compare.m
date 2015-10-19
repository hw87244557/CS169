function [] = compare(audiodata1, audiodata2)
trimmedData1 = trimSync(audiodata1);
trimmedData2 = trimSync(audiodata2);
plot(trimmedData1);
hold;
plot(trimmedData2);
end