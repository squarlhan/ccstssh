
function scrollMarquee() { 
 if(marquee.scrollLeft<marquee.childNodes[0].offsetWidth) 
  marquee.scrollLeft++; 
 else  
  marquee.scrollLeft=0; 
} 
                  
function marquee2()                       
{                   
  marquee.childNodes[0].align='left'; 
  
  if(marquee.childNodes[0].offsetWidth>marquee.offsetWidth) { 
   marquee.appendChild(marquee.childNodes[0].cloneNode(true)); 
   } 
  else { 
   for(var i=0;i<Math.round(marquee.offsetWidth/marquee.childNodes[0].offsetWidth)+1;i++) { 
    marquee.appendChild(marquee.childNodes[0].cloneNode(true)); 
    } 
   } 
  repeat=setInterval(scrollMarquee,1); 
}                       
