const arropa = [
    {
        izena: "Kamiseta beltza",
        prezioa: 10,
        img: "img/arropa/camiseta1.png",
        img2: "img/arropa/camiseta1_puesta.png",
        kategoria: "kamisetak"
    },

    {
        izena: "Kamiseta berdea",
        prezioa: 10,
        img: "img/arropa/camiseta2.png",
        img2: "img/arropa/camiseta2_puesta.png",
        kategoria: "kamisetak"
    },

    {
        izena: "Kamiseta urdina",
        prezioa: 10,
        img: "img/arropa/camiseta3.png",
        img2: "img/arropa/camiseta3_puesta.png",
        kategoria: "kamisetak"
    },

    {
        izena: "Kamiseta gorria",
        prezioa: 10,
        img: "img/arropa/camiseta4.png",
        img2: "img/arropa/camiseta4_puesta.png",
        kategoria: "kamisetak"
    },

    {
        izena: "Kamiseta horia",
        prezioa: 10,
        img: "img/arropa/camiseta5.png",
        img2: "img/arropa/camiseta5_puesta.png",
        kategoria: "kamisetak"
    },

    {
        izena: "Bakero urdin ilunak",
        prezioa: 35,
        img: "img/arropa/pantalon1.png",
        img2: "img/arropa/pantalon1_puesto.png",
        kategoria: "prakak"
    },

    {
        izena: "Txandal beltza",
        prezioa: 35,
        img: "img/arropa/pantalon2.png",
        img2: "img/arropa/pantalon2_puesto.png",
        kategoria: "prakak"
    },

    {
        izena: "Bakero grisak",
        prezioa: 35,
        img: "img/arropa/pantalon3.png",
        img2: "img/arropa/pantalon3_puesto.png",
        kategoria: "prakak"
    },

    {
        izena: "Kargo beige",
        prezioa: 35,
        img: "img/arropa/pantalon4.png",
        img2: "img/arropa/pantalon4_puesto.png",
        kategoria: "prakak"
    },

    {
        izena: "Bakero urdin argiak",
        prezioa: 35,
        img: "img/arropa/pantalon5.png",
        img2: "img/arropa/pantalon5_puesto.png",
        kategoria: "prakak"
    },

    {
        izena: "Jaka beltza",
        prezioa: 60,
        img: "img/arropa/cazadora1.png",
        img2: "img/arropa/cazadora1_puesta.png",
        kategoria: "jakak"
    },

    {
        izena: "Jaka grisa",
        prezioa: 60,
        img: "img/arropa/cazadora2.png",
        img2: "img/arropa/cazadora2_puesta.png",
        kategoria: "jakak"
    },

    {
        izena: "Plumifero urdin iluna",
        prezioa: 60,
        img: "img/arropa/cazadora3.png",
        img2: "img/arropa/cazadora3_puesta.png",
        kategoria: "jakak"
    },

    {
        izena: "Jaka berde iluna",
        prezioa: 60,
        img: "img/arropa/cazadora4.png",
        img2: "img/arropa/cazadora4_puesta.png",
        kategoria: "jakak"
    },

    {
        izena: "Kuerozko jaka beltza",
        prezioa: 60,
        img: "img/arropa/cazadora5.png",
        img2: "img/arropa/cazadora5_puesta.png",
        kategoria: "jakak"
    },

    {
        izena: "Alkandora urdin argia",
        prezioa: 25,
        img: "img/arropa/camisa1.png",
        img2: "img/arropa/camisa1_puesta.png",
        kategoria: "alkandorak"
    },

    {
        izena: "Alkandora urdin argia",
        prezioa: 25,
        img: "img/arropa/camisa2.png",
        img2: "img/arropa/camisa2_puesta.png",
        kategoria: "alkandorak"
    },


    {
        izena: "Alkandora urdin argia",
        prezioa: 25,
        img: "img/arropa/camisa3.png",
        img2: "img/arropa/camisa3_puesta.png",
        kategoria: "alkandorak"
    },


    {
        izena: "Alkandora urdin argia",
        prezioa: 25,
        img: "img/arropa/camisa4.png",
        img2: "img/arropa/camisa4_puesta.png",
        kategoria: "alkandorak"
    },


    {
        izena: "Alkandora urdin argia",
        prezioa: 25,
        img: "img/arropa/camisa5.png",
        img2: "img/arropa/camisa5_puesta.png",
        kategoria: "alkandorak"
    },


    {
        izena: "Sudadera grisa",
        prezioa: 25,
        img: "img/arropa/sudadera1.png",
        img2: "img/arropa/sudadera1_puesta.png",
        kategoria: "sudaderak"
    },

    {
        izena: "Sudadera berdea",
        prezioa: 25,
        img: "img/arropa/sudadera2.png",
        img2: "img/arropa/sudadera2_puesta.png",
        kategoria: "sudaderak"
    },

    {
        izena: "Sudadera urdina",
        prezioa: 25,
        img: "img/arropa/sudadera3.png",
        img2: "img/arropa/sudadera3_puesta.png",
        kategoria: "sudaderak"
    },

    {
        izena: "Sudadera gorria",
        prezioa: 25,
        img: "img/arropa/sudadera4.png",
        img2: "img/arropa/sudadera4_puesta.png",
        kategoria: "sudaderak"
    },

    {
        izena: "Sudadera horia",
        prezioa: 25,
        img: "img/arropa/sudadera5.png",
        img2: "img/arropa/sudadera5_puesta.png",
        kategoria: "sudaderak"
    },
]

const produktuakBistaratu = (bistaratzekoProduktuak) => {
    const kontenidoa = document.getElementById("kontenidoa");

    kontenidoa.innerHTML = ""
    bistaratzekoProduktuak.forEach(produktua => {
        const div = document.createElement("div")
        div.className = "produktua"
        const img2Tag = produktua.img2 ? `<img src="${produktua.img2}" class="img-top" alt="Jarrita">` : '';
        div.innerHTML = `
      <div class="img-container">
    <img src="${produktua.img}" class="img-main" alt="${produktua.izena}">
    ${img2Tag}
  </div>
      <h3>${produktua.izena}</h3>
      <p class="Prezioa"> ${produktua.prezioa} €</p>
      <a href="saskia.html" class="karritora_gehitu_botoia"><button>Karritora gehitu <img src="img/carro.png"></button></a>
    `
        kontenidoa.append(div)
    });
}

const desordenatuArray = (array) => {
    const arrayKopia = [...array];
    for (let i = arrayKopia.length - 1; i > 0; i--) {
        const j = Math.floor(Math.random() * (i + 1));
        [arrayKopia[i], arrayKopia[j]] = [arrayKopia[j], arrayKopia[i]];
    }
    return arrayKopia;
};

const filtratuProduktuak = (kategoria) => {
    const filtratuak = arropa.filter(produktua => produktua.kategoria === kategoria);
    const desordenatuak = desordenatuArray(filtratuak);
    produktuakBistaratu(desordenatuak);
}

const prakakBtn = document.getElementById('prakak');
const kamisetakBtn = document.getElementById('kamisetak');
const sudaderaBtn = document.getElementById('sudaderak');
const alkandorakBtn = document.getElementById('alkandorak');
const jakakBtn = document.getElementById('jakak');
const guztiakBtn = document.getElementById('guztiak');

prakakBtn.addEventListener('click', () => {
    filtratuProduktuak('prakak');
});

kamisetakBtn.addEventListener('click', () => {
    filtratuProduktuak('kamisetak');
});

sudaderaBtn.addEventListener('click', () => {
    filtratuProduktuak('sudaderak');
});

alkandorakBtn.addEventListener('click', () => {
    filtratuProduktuak('alkandorak');
});

jakakBtn.addEventListener('click', () => {
    filtratuProduktuak('jakak');
});

guztiakBtn.addEventListener('click', () => {
    produktuakBistaratu(desordenatuArray(arropa));
});

produktuakBistaratu(desordenatuArray(arropa));

function goraBueltatu() {

    const goraBueltatu = document.getElementById("goraBueltatu");

    window.addEventListener("scroll", () => {
        goraBueltatu.style.display = window.scrollY > 200 ? "block" : "none";
    });

    goraBueltatu.addEventListener("click", () => {
        window.scrollTo({ top: 0, behavior: "smooth" });
    });
}

goraBueltatu();

/* js/produktuak.js - Gehitu hau fitxategiari */

document.addEventListener('DOMContentLoaded', () => {

    // FILTROEN MENU MUGIKORRAREN LOGIKA
    const filterToggle = document.getElementById('filterToggle');
    const filterMenu = document.getElementById('filtroMenu');

    if (filterToggle && filterMenu) {
        filterToggle.addEventListener('click', () => {
            // Menua erakutsi/ezkutatu
            filterMenu.classList.toggle('active');
            // Botoiari estiloa aldatu (gezia biratzeko adibidez)
            filterToggle.classList.toggle('active');
        });
    }

    // Aukera bat klikatzean menua ixtea (nahi baduzu)
    const filterButtons = document.querySelectorAll('.filtro_elementuak button');
    filterButtons.forEach(btn => {
        btn.addEventListener('click', () => {
            // Pantaila txikia bada bakarrik itxi
            if (window.innerWidth <= 768) {
                filterMenu.classList.remove('active');
                filterToggle.classList.remove('active');
            }
        });
    });

    // ... Hemen zure produktuen filtro logika egongo litzateke ...
});