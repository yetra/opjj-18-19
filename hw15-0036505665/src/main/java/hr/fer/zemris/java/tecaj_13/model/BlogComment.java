package hr.fer.zemris.java.tecaj_13.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Models a blog comment posted by a user on a given blog entry.
 */
@Entity
@Table(name = "blog_comments")
public class BlogComment {

	/**
	 * The maximum length of the comment creator's e-mail.
	 */
	public static final int EMAIL_LENGTH = 100;

	/**
	 * The maximum length of the comment message.
	 */
	public static final int MESSAGE_LENGTH = 4096;

	/**
	 * The ID of this comment.
	 */
	private Long id;

	/**
	 * The blog entry that this comment belongs to.
	 */
	private BlogEntry blogEntry;

	/**
	 * The e-mail of the user who created this comment.
	 */
	private String usersEMail;

	/**
	 * The message of this comment.
	 */
	private String message;

	/**
	 * This comment's date of creation.
	 */
	private Date postedOn;

	/**
	 * Returns the ID of this comment.
	 *
	 * @return the ID of this comment
	 */
	@Id @GeneratedValue
	public Long getId() {
		return id;
	}

	/**
	 * Sets the ID of this comment to the given value.
	 *
	 * @param id the ID to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Returns the blog entry that this comment belongs to.
	 *
	 * @return the blog entry that this comment belongs to
	 */
	@ManyToOne
	@JoinColumn(nullable = false)
	public BlogEntry getBlogEntry() {
		return blogEntry;
	}

	/**
	 * Sets the blog entry that this comment belongs to to the given value.
	 *
	 * @param blogEntry the blog entry to set
	 */
	public void setBlogEntry(BlogEntry blogEntry) {
		this.blogEntry = blogEntry;
	}

	/**
	 * Returns the e-mail of the user who created this comment.
	 *
	 * @return the e-mail of the user who created this comment.
	 */
	@Column(length = EMAIL_LENGTH, nullable = false)
	public String getUsersEMail() {
		return usersEMail;
	}

	/**
	 * Sets the e-mail of the user who created this comment to the given value.
	 *
	 * @param usersEMail the user email to set
	 */
	public void setUsersEMail(String usersEMail) {
		this.usersEMail = usersEMail;
	}

	/**
	 * Returns the message of this comment.
	 *
	 * @return the message of this comment
	 */
	@Column(length = MESSAGE_LENGTH, nullable = false)
	public String getMessage() {
		return message;
	}

	/**
	 * Sets the message of this comment to the given value.
	 *
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Returns this comment's date of creation.
	 *
	 * @return this comment's date of creation
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	public Date getPostedOn() {
		return postedOn;
	}

	/**
	 * Sets this comment's date of creation to the given value.
	 *
	 * @param postedOn the date of creation to set
	 */
	public void setPostedOn(Date postedOn) {
		this.postedOn = postedOn;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BlogComment other = (BlogComment) obj;
		if (id == null) {
			return other.id == null;
		} else return id.equals(other.id);
	}
}