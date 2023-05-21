import{a as p}from"./axios-aba6f0e0.js";import{_ as v,o as a,a as n,b as t,k as h,l as d,v as u,F as f,i as _,t as m,n as c,m as y,p as T,q as b}from"./index-f9d7a4ee.js";const g="vue-todomvc",r={all:e=>e,active:e=>e.filter(l=>!l.completed),completed:e=>e.filter(l=>l.completed)},k={todos:[],data:()=>({todos:JSON.parse(localStorage.getItem(g)||"[]"),editedTodo:null,visibility:"all"}),watch:{todos:{handler(e){localStorage.setItem(g,JSON.stringify(e))},deep:!0}},mounted(){window.addEventListener("hashchange",this.onHashChange),this.onHashChange()},computed:{filteredTodos(){return r[this.visibility](this.todos)},remaining(){return r.active(this.todos).length}},methods:{toggleAll(e){this.todos.forEach(l=>l.completed=e.target.checked)},fetchToDos(e){p.get("http://localhost:8080/users/todo/all").then(l=>{this.todos=l.data}).catch(l=>{console.log(l)})},addTodo(e){const l=e.target.value.trim();l&&(this.todos.push({id:Date.now(),title:l,completed:!1}),e.target.value="")},removeTodo(e){this.todos.splice(this.todos.indexOf(e),1)},editTodo(e){this.beforeEditCache=e.title,this.editedTodo=e},doneEdit(e){this.editedTodo&&(this.editedTodo=null,e.title=e.title.trim(),e.title||this.removeTodo(e))},cancelEdit(e){this.editedTodo=null,e.title=this.beforeEditCache},removeCompleted(){this.todos=r.active(this.todos)},onHashChange(){var e=window.location.hash.replace(/#\/?/,"");r[e]?this.visibility=e:(window.location.hash="",this.visibility="all")}}},C={class:"todoapp"},w={class:"header"},E={class:"main"},D=["checked"],S=t("label",{for:"toggle-all"},"Mark all as complete",-1),V={class:"todo-list"},A={class:"view"},B=["onUpdate:modelValue"],K=["onDblclick"],M=["onClick"],N=["onUpdate:modelValue","onBlur","onKeyup"],O={class:"footer"},U={class:"todo-count"},x={class:"filters"};function H(e,l,F,I,J,s){return a(),n("section",C,[t("header",w,[t("input",{class:"new-todo",autofocus:"",placeholder:"What needs to be done?",onKeyup:l[0]||(l[0]=h((...o)=>s.addTodo&&s.addTodo(...o),["enter"]))},null,32)]),d(t("section",E,[t("input",{id:"toggle-all",class:"toggle-all",type:"checkbox",checked:s.remaining===0,onChange:l[1]||(l[1]=(...o)=>s.toggleAll&&s.toggleAll(...o))},null,40,D),S,t("ul",V,[(a(!0),n(f,null,_(s.filteredTodos,o=>(a(),n("li",{class:c(["todo",{completed:o.completed,editing:o===e.editedTodo}]),key:o.id},[t("div",A,[d(t("input",{class:"toggle",type:"checkbox","onUpdate:modelValue":i=>o.completed=i},null,8,B),[[y,o.completed]]),t("label",{onDblclick:i=>s.editTodo(o)},m(o.title),41,K),t("button",{class:"destroy",onClick:i=>s.removeTodo(o)},null,8,M)]),o===e.editedTodo?d((a(),n("input",{key:0,class:"edit",type:"text","onUpdate:modelValue":i=>o.title=i,onVnodeMounted:l[2]||(l[2]=({el:i})=>i.focus()),onBlur:i=>s.doneEdit(o),onKeyup:[h(i=>s.doneEdit(o),["enter"]),h(i=>s.cancelEdit(o),["escape"])]},null,40,N)),[[T,o.title]]):b("",!0)],2))),128))])],512),[[u,e.todos.length]]),d(t("footer",O,[t("span",U,[t("strong",null,m(s.remaining),1),t("span",null,m(s.remaining===1?" item":" items")+" left",1)]),t("ul",x,[t("li",null,[t("a",{href:"#/all",class:c({selected:e.visibility==="all"})},"All",2)]),t("li",null,[t("a",{href:"#/active",class:c({selected:e.visibility==="active"})},"Active",2)]),t("li",null,[t("a",{href:"#/completed",class:c({selected:e.visibility==="completed"})},"Completed",2)])]),d(t("button",{class:"clear-completed",onClick:l[3]||(l[3]=(...o)=>s.removeCompleted&&s.removeCompleted(...o))}," Clear completed ",512),[[u,e.todos.length>s.remaining]])],512),[[u,e.todos.length]])])}const q=v(k,[["render",H]]);export{q as default};
