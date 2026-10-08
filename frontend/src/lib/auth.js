export const getUser=()=>JSON.parse(localStorage.getItem('playzone_user')||'null');
export const saveAuth=(data)=>{localStorage.setItem('playzone_token',data.token);localStorage.setItem('playzone_user',JSON.stringify(data.user));};
export const logout=()=>{localStorage.removeItem('playzone_token');localStorage.removeItem('playzone_user');};
