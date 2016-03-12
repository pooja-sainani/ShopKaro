using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Threading.Tasks;
using APIShopKaro.Models;
namespace APIShopKaro.Services
{
    public class CartService
    {
        /// <summary>
        /// Add new product to Cart
        /// </summary>
        /// <param name="product"></param>
        /// <returns></returns>
        public Guid AddProductInCart(CART product)
        {
            try
            {
                CART productcart=null;
                product.ISACTIVE = true;
                if (product == null)
                    throw new ArgumentNullException("Product", "Product can not be null");

                // check if all required fields are present
                if ((product.PRODUCTID == null || product.PRODUCTID == Guid.Empty) || product.QUANTITY == null ||
                    (product.BUYERID == null || product.BUYERID == Guid.Empty))
                    throw new ArgumentException("Some mandatory parameters required to add a new product are missing", "Product");
                try
                {
                    using (APIShopKaro.Models.apsteamCFHEntities db = new APIShopKaro.Models.apsteamCFHEntities())
                    {
                        productcart = (from p in db.CARTs
                                       where p.BUYERID == product.BUYERID && p.PRODUCTID == product.PRODUCTID
                                       select p).Single();
                    }
                    return productcart.ID;
                }
                catch (Exception e)
                {

                }

                if (product.ID==null || product.ID == Guid.Empty)
                    product.ID = Guid.NewGuid();

                using (APIShopKaro.Models.apsteamCFHEntities db = new APIShopKaro.Models.apsteamCFHEntities())
                {
                    try
                    {
                        db.CARTs.Add(product);
                        db.SaveChanges();
                    }
                    catch (System.Data.DataException e)
                    {
                        throw new Exception(e.InnerException.InnerException.Message);
                    }
                }

                return product.ID;
            }
            catch (Exception e)
            {
                throw;
            }
        }

        /// <summary>
        /// Get all products for a Cart
        /// </summary>
        /// <param name="buyerId"></param>
        /// <returns></returns>
        public List<CartProduct> GetAllCartProducts(Guid? buyerId)
        {
            try
            {
                if (!buyerId.HasValue || buyerId.Value == Guid.Empty)
                    throw new ArgumentNullException("BuyerID", "BuyerId can not be null");

                List<CartProduct> products = new List<CartProduct>();
                
                using (APIShopKaro.Models.apsteamCFHEntities db = new APIShopKaro.Models.apsteamCFHEntities())
                {
                    try
                    {
                        products = (from p in db.CARTs
                                    join pr in db.PRODUCTS on p.PRODUCTID equals pr.ID
                                    where p.BUYERID == buyerId
                                    select new CartProduct { ID = p.ID, PRODUCTID = p.PRODUCTID, QUANTITY = p.QUANTITY, BUYERID = p.BUYERID, ISACTIVE = p.ISACTIVE, ProductName = pr.NAME,Price = (Decimal)pr.PRICE }).ToList();
                    }
                    catch (System.Data.DataException e)
                    {
                        throw new Exception(e.InnerException.InnerException.Message);
                    }
                    catch (ArgumentNullException e)
                    {
                    }
                }
                return products;
            }
            catch (Exception e)
            {
                throw;
            }
        }

        /// <summary>
        /// delete product from cart
        /// </summary>
        /// <param name="id"></param>
        /// <returns></returns>
        public bool DeleteProduct(Guid? id)
        {
            try
            {
                if (!id.HasValue || id.Value == Guid.Empty)
                    throw new ArgumentNullException("ID", "ID can not be null");

                using (APIShopKaro.Models.apsteamCFHEntities db = new APIShopKaro.Models.apsteamCFHEntities())
                {
                    try
                    {
                        var product = (from s in db.CARTs
                                       where s.ID == id
                                       select s).Single();
                        db.CARTs.Remove(product);
                       db.SaveChanges();
                        return true;
                    }
                    catch (System.Data.DataException e)
                    {
                        throw new Exception(e.InnerException.InnerException.Message);
                    }
                }
            }
            catch (Exception e)
            {
                throw;
            }
        }

    }
}