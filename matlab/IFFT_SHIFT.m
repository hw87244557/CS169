% ����Ҷ���任
function  [t,st]=IFFT_SHIFT(f,Sf)
df=f(2)-f(1);
fmax=(f(end)-f(1)+df);
dt=1/fmax;
N=length(f);
t=[0:N-1]*dt;
Sf=fftshift(Sf);
st=fmax*ifft(Sf);
st=real(st);
end