/*
 * User: Robbix
 * Date: 2/12/2006
 * Time: 4:42 PM
 */

using System;

namespace Robbix.Utilities
{

	public class Test
	{
		
		public static void Main(string[] args)
		{
			
			LinkedList List = new LinkedList();
			
			// LinkedList Test
			Show(List);
			List.AddFirst("Item One");
			Show(List);
			
			// Stack Test
			
			// Queue Test
			
		}
		
		private static void Show(LinkedList List)
		{
			
			Console.Write("Head->");
			
			for (uint Index = 0; Index < List.GetSize(); ++Index)
				Console.Write(List.Get(Index));
			
			Console.WriteLine("Head");
			
		}
		
	}
	
	public interface IList
	{
		
	    uint GetSize();
		
		object Get(uint Index);
		
		IList Set(uint Index, object Element);
		
	}
	
	public interface IStack
	{
		
		uint GetSize();
		
		IStack Enstack(object Element);
		
		object Pop();
		
		object Peek();
		
	}
	
	public interface IQueue
	{
		
		uint GetSize();
		
		IQueue Enqueue(object Element);
		
		object Pop();
		
		object Peek();
		
	}
	
	public class LinkedList : IList, IStack, IQueue
	{
		
		private Node Head = new Node("Head", null, null);
		private uint Size = 0;
		
		public LinkedList()
		{
			
			Clear();
			
		}
		
		public uint GetSize()
		{
			
			return Size;
			
		}
		
		public object Get(uint Index)
		{
			
			return GetNode(Index).Element;
			
		}
		
		public IList Set(uint Index, object Element)
		{
			
			GetNode(Index).Element = Element;
			return this;
			
		}
		
		private Node GetNode(uint Index)
		{
			
			if (Index < 0 || Index > Size)
				return null;
			
			Node CurrentNode = Head;
			
			if (Index < (Size / 2))
			{
				
				for (uint Position = 0; Position <= Index; Position++)
					CurrentNode = CurrentNode.Next;
				
			}
			else
			{
				
				for (uint Position = Size - 1; Position >= Index; Position--)
					CurrentNode = CurrentNode.Previous;
				
			}
				
			return CurrentNode;
			
		}
		
		public IQueue Enqueue(object Element)
		{
			
			return AddLast(Element);
			
		}
		
		public IStack Enstack(object Element)
		{
			
			return AddFirst(Element);
			
		}
		
		public object Pop()
		{
			
			return RemoveFirst();
			
		}
		
		public object Peek()
		{
			
			return GetFirst();
			
		}
		
		public LinkedList Clear()
		{
			
			Head.Next = Head.Previous = Head;
			return this;
			
		}
		
		public object GetFirst()
		{
			
			return Head.Next.Element;
			
		}
		
		public object GetLast()
		{
			
			return Head.Previous.Element;
			
		}
		
		public object RemoveFirst()
		{
			
			return RemoveNode(Head.Next).Element;
			
		}
		
		public object RemoveLast()
		{
			
			return RemoveNode(Head.Previous).Element;
			
		}
		
		public LinkedList AddFirst(object Element)
		{
			
			AddNode(Element, Head.Next);
			return this;
			
		}
		
		public LinkedList AddLast(object Element)
		{
			
			AddNode(Element, Head);
			return this;
			
		}
		
		private Node AddNode(object Element, Node Entry)
		{
			
			Node NewEntry = new Node(Element, Entry.Previous, Entry);
			NewEntry.Previous.Next = NewEntry;
			NewEntry.Next.Previous = NewEntry;
			Size++;
			return NewEntry;
			
		}
	
		private Node RemoveNode(Node Entry)
		{
			
			if (Entry == Head)
				return null;
			
			Entry.Previous.Next = Entry.Next;
			Entry.Next.Previous = Entry.Previous;
			Entry.Next = null;
			Entry.Previous = null;
			Size--;
			return Entry;
			
		}

		private class Node
		{
			
			public Node Previous, Next;
			public object Element;
			
			public Node(object Element, Node Previous, Node Next)
			{
				
				this.Element = Element;
				this.Previous = Previous;
				this.Next = Next;
				
			}
			
		}
		
	}

}
