import{J as m,K as f,u as d,j as w,r,B as n}from"./index-w_VBQHr5.js";const u=m();function g(l,o){u.get("/members/teams").then(l).catch(o)}function F(l,o,s){u.get(`/members/${l}/follows`).then(o).catch(s)}function h(l,o,s){u.post("/members/follows",l).then(o).catch(s)}function I(l,o,s){u.delete(`/members/follows/${l}`).then(o).catch(s)}const S=f("follow",()=>{d(),w();const l=localStorage.getItem("id"),o=r(null),s=r(null),i=()=>{g(({data:e,status:a})=>{if(a===n.OK)return o.value=e.data,o.value},e=>{console.error(e),e.status===n.NOTFOUND&&console.log(e)})},c=e=>{F(e,({data:a,status:t})=>{console.log(a),t===n.OK&&(s.value=a.data)})};return{getTeamList:i,getFollowList:c,doFollow:e=>{const a={memberId:localStorage.getItem("id"),teamId:e};h(a,t=>{t.status===n.CREATE&&(c(l),alert("팔로우 완료!"))},t=>{t.status===n.NOTFOUND&&alert("팔로우 실패!")})},doUnFollow:e=>{c(localStorage.getItem("id"));const a=s.value.filter(t=>t.teamId===e).map(t=>t.id)[0];I(a,t=>{t.status===n.OK&&(c(l),alert("팔로우 취소 완료"))},t=>{t.status===400&&alert("팔로우 취소 실패!")})},teamList:o,followList:s}});export{S as u};
