%频率不同的三个正弦波信号相加，分别输出时域波形和频域频谱 three_cos.m
%设计低通滤波器，改变低通滤波器截止频率，输出不同波形
%1.波形产生程序 three_cos(t)；
%2.傅里叶正变换[f, sf]=FFT_SHIFT(t, st)；
%3.傅里叶反变换[t,st]=IFFT_SHIFT(f,Sf)；
%4.低通滤波器[t,st]=RECT_LPF(f,Sf,B)；


% Signal
dt=0.01;    %时间采样间隔
T=5;        %信号持续时间
N=floor(T/dt);  %点数
t=[0:N-1]*dt;   %t


f1=1;f2=5;f3=10; %信号不同频率值
m1=cos(2*pi*f1*t);
m2=cos(2*pi*f2*t);
m3=cos(2*pi*f3*t);
mt1=m1+m2+m3;
[f,mt1f]=FFT_SHIFT(t,mt1); %2.傅里叶正变换 
     %输出:f-抽样频率；mt1f-频率；输入:t-时间；mt1-输入波形
fmax=2;f1=1;f2=5;f3=10; %信号不同频率值
B1=fmax; %设置低通滤波器带宽，准备调用低通滤波器
[t,mt1_t]=RECT_LPF(f,mt1f,B1); %低通滤波器滤除高频，由B1决定
[f,mt1_tf]=FFT_SHIFT(t,mt1_t); %2.傅里叶正变换 


 %时域、频域显示
subplot(511);
plot(t,m1);
ylabel('m1(t)');
title('三个不同频率正弦波合成波和频谱');
subplot(512);
plot(t,m2);
ylabel('m2(t)');
subplot(513);
plot(t,m3);
ylabel('m3(t)');
subplot(514);
plot(t,mt1);
ylabel('m(t)');
subplot(515);
plot(f,mt1f);
ylabel('m(f)');
axis([-40,40,0,3]);


figure(2)
subplot(511);
plot(t,m1);
ylabel('m1(t)');
title('低通滤波器选频合成波和频谱');
subplot(512);
plot(t,m2);
ylabel('m2(t)');
subplot(513);
plot(t,m3);
ylabel('m3(t)');
subplot(514);
plot(t,mt1_t);
ylabel('m(t)');
subplot(515);
plot(f,mt1_tf);
ylabel('fmax=2');
axis([-40,40,0,3]);