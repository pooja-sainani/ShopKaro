using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace APIShopKaro.Models
{
    public class OrderProducts
    {
        public int QUANTITY { get; set; }
   
        public String ProductName { get; set; }

        public Decimal Price { get; set; }

    }
}