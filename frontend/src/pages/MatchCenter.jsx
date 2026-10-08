import {useEffect,useState} from 'react';
import {useParams} from 'react-router-dom';
import api from '../lib/api';
import {getUser} from '../lib/auth';

export default function MatchCenter(){
 const {id}=useParams(); const u=getUser(); const [matches,setMatches]=useState([]); const [users,setUsers]=useState([]); const [err,setErr]=useState(''); const [busy,setBusy]=useState(false);
 const load=()=>api.get(`/matches/tournament/${id}/bracket`).then(r=>setMatches(r.data));
 useEffect(()=>{load();api.get('/leaderboard').then(r=>setUsers(r.data)).catch(()=>{})},[id]);
 const name=(uid)=>users.find(x=>x.id===uid)?.username||uid||'BYE';
 const report=async(m)=>{
   if(!u)return; setErr(''); const entered=window.prompt(`Winner username: ${name(m.player1Id)} or ${name(m.player2Id)}`); const w=users.find(x=>x.username.toLowerCase()===entered?.trim().toLowerCase()); if(!w)return;
   try{setBusy(true);await api.post(`/matches/${m.id}/result`,{winnerId:w.id,evidenceUrl:null});await load()}catch(e){setErr(e.response?.data?.error||'Could not submit result')}finally{setBusy(false)}
 };
 const rounds=[...new Set(matches.map(m=>m.round))].sort((a,b)=>a-b);
 return <section className="page"><div className="section-head"><div><div className="eyebrow">⚔ MATCH CENTER</div><h1>Live bracket</h1><p className="lead">Results are reviewed before they become official.</p></div></div>{err&&<div className="error">{err}</div>}<div className="bracket">{rounds.map(r=><div className="round" key={r}><h3>{r===1?'ROUND 1':r===rounds.at(-1)?'FINAL':`ROUND ${r}`}</h3>{matches.filter(m=>m.round===r).map(m=><div className="match-card" key={m.id}><div className={m.winnerId===m.player1Id?'winner':''}>{name(m.player1Id)}</div><div className={m.winnerId===m.player2Id?'winner':''}>{name(m.player2Id)}</div><span className="pill">{m.status}</span>{m.status==='SCHEDULED'&&(m.player1Id===u?.id||m.player2Id===u?.id)&&<button disabled={busy} className="btn btn-small" onClick={()=>report(m)}>Report result</button>}</div>)}</div>)}</div></section>
}
