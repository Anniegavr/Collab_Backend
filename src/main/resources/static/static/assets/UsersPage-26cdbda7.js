import{a as _}from"./axios-aba6f0e0.js";import{_ as u,e as p,o as a,a as i,b as e,f,F as g,i as x,t as r}from"./index-f9d7a4ee.js";import{S as U}from"./SearchField-9361ba50.js";import{S as b}from"./SearchIcon-55360174.js";const k={name:"UsersPage",id:"",email:"",password:"",components:{SearchField:U,SearchIcon:b},data(){return{users:this.getAllUsers(),searchTerm:""}},methods:{getAllUsers(s){_.get("http://localhost:8080/admin/users/all").then(t=>{this.users=t.data}).catch(t=>{console.log(t)})},editUser(s){const t=this.users.findIndex(n=>n.id===s.id);if(t!==-1){const n=prompt("Enter the new name:",s.name),l=prompt("Enter the new email:",s.email);n&&l&&(this.users[t].name=n,this.users[t].email=l)}},deleteUser(s){const t=this.users.findIndex(n=>n.id===s.id);t!==-1&&confirm(`Are you sure you want to delete ${s.name}?`)&&this.users.splice(t,1)}}},v={id:"app"},y={class:"admin-panel"},S={class:"common_table"},w=e("thead",null,[e("tr",null,[e("th",null,"ID"),e("th",null,"Name"),e("th",null,"Email"),e("th",null,"Actions")])],-1),E=["onClick"],C=["onClick"];function F(s,t,n,l,c,d){const m=p("SearchField");return a(),i("div",v,[e("div",y,[f(m,{"search-term":c.searchTerm},null,8,["search-term"]),e("table",S,[w,e("tbody",null,[(a(!0),i(g,null,x(c.users,o=>(a(),i("tr",{key:o.id},[e("td",null,r(o.id),1),e("td",null,r(o.name),1),e("td",null,r(o.email),1),e("td",null,[e("button",{class:"edit-btn",onClick:h=>d.editUser(o)},"Edit",8,E),e("button",{class:"delete-btn",onClick:h=>d.deleteUser(o)},"Delete",8,C)])]))),128))])])])])}const D=u(k,[["render",F]]);export{D as default};