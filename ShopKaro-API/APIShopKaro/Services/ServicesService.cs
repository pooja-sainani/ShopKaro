using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using APIShopKaro.Models;

namespace APIShopKaro.Services
{
    public class ServicessService
    {

        /// <summary>
        /// Add new service
        /// </summary>
        /// <param name="service"></param>
        /// <returns></returns>
        public Guid AddNewService(SERVICE service)
        {
            try
            {
                if (service == null)
                    throw new ArgumentNullException("Service", "Service can not be null");

                // check if all required fields are present
                if ((!service.CATEGORYID.HasValue || service.CATEGORYID.Value == Guid.Empty) || service.NAME == null ||
                    !service.PRICE.HasValue || (!service.SELLERID.HasValue || service.SELLERID.Value == Guid.Empty))
                    throw new ArgumentException("Service", "Some mandatory parameters required to add a new service are missing");

                if (!service.ID.HasValue || service.ID.Value == Guid.Empty)
                    service.ID = Guid.NewGuid();

                using (APIShopKaro.Models.apsteamCFHEntities db = new APIShopKaro.Models.apsteamCFHEntities())
                {
                    try
                    {
                        db.SERVICES.Add(service);
                        db.SaveChanges();
                    }
                    catch (System.Data.DataException e)
                    {
                        throw new Exception(e.InnerException.InnerException.Message);
                    }
                    catch (Exception e)
                    {
                        throw;
                    }
                }

                return service.ID.Value;
            }
            catch (Exception e)
            {
                throw;
            }
        }
    }
}