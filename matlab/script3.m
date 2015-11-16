audiodata0 = csvread('./outputfile5.csv');

figure
plot(audiodata0);

patch_size = 100;
length = size(audiodata0,1);
trim_length = floor(length/patch_size)*patch_size;
trim_data = audiodata0(1:trim_length);
envelope = max(reshape(trim_data, patch_size,trim_length/patch_size));

% figure
% plot(envelope);

% med_filt_envelope = medfilt1(envelope,20);

med_filt_envelope = zeros(1,size(envelope,2) - 20);
for i = 1:size(envelope,2) - 20
    med_filt_envelope(i) = round(sum(envelope(i:i+19))/20);
end



figure
plot(med_filt_envelope);
hold;

half_patch_size = 10;
length = size(med_filt_envelope,2);
diff = zeros(1,length);
min_value = 0;
max_value = 0;
for i = 1+patch_size:length-patch_size
    if med_filt_envelope(i) == min(med_filt_envelope(i-half_patch_size:i+half_patch_size))
        plot(i,med_filt_envelope(i),'.r');
        min_value = med_filt_envelope(i);
    end
    if med_filt_envelope(i) == max(med_filt_envelope(i-half_patch_size:i+half_patch_size))
        plot(i,med_filt_envelope(i),'.g');
        max_value = med_filt_envelope(i);
    end
    diff(i) = max_value - min_value;
end

figure
plot(diff);
