import{a as o}from"./axios-aba6f0e0.js";import{_ as a,o as n,a as r,b as e,t as l}from"./index-f9d7a4ee.js";const c={name:"TotalRequests",data(){return{totalRequests:0}},created(){o.get("http://localhost:8080/admin/requests/total").then(t=>{this.totalRequests=t.data}).catch(t=>{console.error(t)})}},_=e("h2",null,"Total Requests",-1),u={class:"result"};function i(t,d,p,h,s,m){return n(),r("div",null,[_,e("div",u,l(s.totalRequests),1)])}const f=a(c,[["render",i]]);export{f as T};