/* Se o valor dado ao 'img' == null o valor é trocado por 'img' == ERRO, com isso 
 * a função substitui por um p == uma msg 
 * */
function ImagemErro(imagem){
	if(imagem == "ERRO"){
		let imgErro = document.getElementById('ERRO');
		let p = document.createElement('p');
		p.innerHTML = "<b>Sem Imagem</b>";
		imgErro.parentNode.replaceChild(p, imgErro);
	}
}

/* Se o valor do codigo da imagem != undefined a função retorna um p vazio
 * Se o valor do codigo da imagem == undefined a funçãoe retorna um p com uma msg
 * */
function AusenciaImagem(objeto, codImagem){
	if(codImagem == undefined){
		let p = document.createElement('p');
		p.innerHTML = "";
		objeto.parentNode.replaceChild(p, objeto);
	}else{
		let p = document.createElement('p');
		p.innerHTML = "<b>Sem Imagem</b>";
		objeto.parentNode.replaceChild(p, objeto);
	}
}

/* Função que troca o objeto html para hide ou show (trocando none ou initial dependendo da propriedade atual) */
function Temporizador(obj, time){
	object = document.getElementById(obj);
	if(object.style.display != "none"){
		setTimeout(function(){
			object = document.getElementById(obj);
			object.style.display = "none";
		}, time)
	}else{
		object = document.getElementById(obj);
		object.style.display = "initial";
		
		Temporizador(obj, time);
	}
}

function AncoragemTopoPagina(){
	window.scroll(0,0);
}

function ImprimirObjetosDocumentHtml(){
	
}

/* Util
	sessionStorage.setItem("recarregou", "true"); // antes de atualizar, você seta uma variável no sessionStorage como true
	window.location.reload(); // atualiza a página
	sessionStorage.removeItem("recarregou"); // remove a variável
*/