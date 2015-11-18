audiodata0 = csvread('./outputfile5.csv');
figure
plot(audiodata0);


% Signal
dt=1/44100;                 %时间采样间隔
T=size(audiodata0,1)/44100; %信号持续时间
N=floor(T/dt);  %点数
t=[0:N-1]*dt;   %t

[f,mt1f]=FFT_SHIFT(t,audiodata0); %2.傅里叶正变换 
%输出:f-抽样频率；mt1f-频率；输入:t-时间；mt1-输入波形

fmax=12000;f1=1;f2=5;f3=10; %信号不同频率值
B1=fmax; %设置低通滤波器带宽，准备调用低通滤波器
[t,mt1_t]=RECT_HPF(f,mt1f',B1); %低通滤波器滤除高频，由B1决定
[f,mt1_tf]=FFT_SHIFT(t,mt1_t); %2.傅里叶正变换
mt1_t = fliplr(mt1_t);

%时域、频域显示
subplot(311);
plot(t,audiodata0);
ylabel('audiodata0');

subplot(312);
plot(f,mt1f);
ylabel('m(f)');

subplot(313);
plot(t,mt1_t);
ylabel('recover signal');