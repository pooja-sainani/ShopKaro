using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace APIShopKaro.Models
{
    public class SubscribedService
    {

        public System.Guid? ID { get; set; }
        public String Name { get; set; }

        public Decimal? Price { get; set; }

        public String Details { get; set; }

        public String SellerName { get; set; }

        public String SellerContact { get; set; }


        public String SellerPlace { get; set; }

        public String SellerCity { get; set; }

        



    }
}