function trimmedAudioData = trimSync(audiodata)
a = 0;
for i = 50000 : 1 : 250000
	if audiodata(i) > 5000
		a = i;
		break;
	end
end
trimmedAudioData = audiodata(a -10000 : a + 140000 - 1);
end