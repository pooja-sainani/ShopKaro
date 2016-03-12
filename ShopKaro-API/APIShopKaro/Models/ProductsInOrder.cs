using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace APIShopKaro.Models
{
    public class ProductsInOrder
    {

        public System.Guid PRODUCTID { get; set; }
        public int QUANTITY { get; set; }
        public System.Guid BUYERID { get; set; }

        public Decimal Price { get; set; }
    }
}