package xyz.arunangshu.msscbeerorderservice.domain;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
public class BeerOrder extends BaseEntity {

    @Builder
    public BeerOrder(
            UUID id,
            Long version,
            Timestamp createdDate,
            Timestamp lastModifiedDate,
            String customerRef,
            Customer customer,
            Set<BeerOrderLine> beerOrderLines,
            OrderStatusEnum orderStatus,
            String orderStatusCallbackUrl) {
        super(id, version, createdDate, lastModifiedDate);
        this.customerRef = customerRef;
        this.customer = customer;
        this.beerOrderLines = beerOrderLines;
        this.orderStatus = orderStatus;
        this.orderStatusCallbackUrl = orderStatusCallbackUrl;
    }

    private String customerRef;

    @ManyToOne
    private Customer customer;

    @OneToMany(mappedBy = "beerOrder", cascade = CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    @ToString.Exclude
    private Set<BeerOrderLine> beerOrderLines;

    private OrderStatusEnum orderStatus = OrderStatusEnum.NEW;

    private String orderStatusCallbackUrl;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        BeerOrder beerOrder = (BeerOrder) o;
        return getId() != null && Objects.equals(getId(), beerOrder.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
