import{J as l,K as u,r as h,B as o}from"./index-w_VBQHr5.js";const i=l(),r="/archives";function v(t,c,a){i.get(`${r}/clubs/${t}`).then(c).catch(a)}function A(t,c,a){i.get(`${r}/${t}`).then(c).catch(a)}function f(t,c,a){i.post(`${r}`,t).then(c).catch(a)}function d(t,c,a){i.put(`${r}`,t).then(c).catch(a)}function g(t,c,a){i.delete(`${r}/${t}`).then(c).catch(a)}const L=u("archive",()=>{const t=h([]),c=h({}),a=s=>{v(s,e=>{e.status===o.OK&&(t.value=e.data.data)},e=>{console.log(e)})};return{getArchiveList:a,getArchiveDetail:s=>{A(s,e=>{e.status===o.OK&&(c.value=e.data.data)},e=>{console.log(e)})},createArchive:s=>{f(s,e=>{e.status===o.CREATE&&alert("추억이 잘 간직되었습니다!")},e=>{console.log(e)})},updateArchive:s=>{d(s,e=>{e.status===o.OK&&alert("추억정보가 잘 수정되었습니다!")},e=>{console.log(e)})},deleteArchive:(s,e)=>{g(s,n=>{n.status===o.OK&&(alert("추억이 사라졌습니다!"),a(e))},n=>{console.log(n)})},archiveList:t,archiveDetail:c}});export{L as u};